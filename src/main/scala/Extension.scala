package anorm

import anorm._

object Extension{

  implicit def rowToFloat: Column[Float] = Column.nonNull { (value, meta) =>
    val MetaDataItem(qualified, nullable, clazz) = meta
    value match {
      case f: Float => Right(f)
      case _ => Left(TypeDoesNotMatch("Cannot convert " + value + ":" + value.asInstanceOf[AnyRef].getClass + " to Float for column " + qualified))
    }
  }

  implicit def rowToByteArray: Column[Array[Byte]] = Column.nonNull { (value, meta) =>
    val MetaDataItem(qualified, nullable, clazz) = meta
    value match {
      case blob: java.sql.Blob => Right(blob.getBytes(0, blob.length.toInt))
      case _ => Left(TypeDoesNotMatch("Can't convert to byte array " + value + ":" + value.asInstanceOf[AnyRef].getClass) )
    }
  }

	implicit val byteArrayToStatement = new ToStatement[Array[Byte]] {
    def set(s: java.sql.PreparedStatement, index: Int, aValue: Array[Byte]): Unit = s.setBytes(index, aValue)
  }
}