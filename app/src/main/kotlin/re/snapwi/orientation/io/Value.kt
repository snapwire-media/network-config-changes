package re.snapwi.orientation.io

import android.os.Parcel
import android.os.Parcelable

public class Value : Parcelable {
  public var joke: String? = null
  public var id: String? = null

  public constructor(parcel: Parcel) {
    joke = parcel.readString()
    id = parcel.readString()
  }

  override fun describeContents(): Int = 0

  override fun writeToParcel(dest: Parcel, flags: Int) {
    dest.writeString(joke)
    dest.writeString(id)
  }

  companion object {
    public val CREATOR: Parcelable.Creator<Value> = object : Parcelable.Creator<Value> {
      override fun createFromParcel(parcel: Parcel): Value = Value(parcel)
      override fun newArray(size: Int): Array<Value?> = arrayOfNulls(size)
    }
  }
}
