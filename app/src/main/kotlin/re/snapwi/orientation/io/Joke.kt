package re.snapwi.orientation.io

import android.os.Parcel
import android.os.Parcelable

public class Joke : Parcelable {
  var value: Value? = null

  public constructor(parcel: Parcel) {
    value = parcel.readValue(javaClass<Value>().getClassLoader()) as Value
  }

  public fun getJoke(): String = value?.joke ?: "";

  override fun describeContents(): Int = 0

  override fun writeToParcel(dest: Parcel, flags: Int) {
    dest.writeValue(value)
  }

  companion object {
    public val CREATOR: Parcelable.Creator<Joke> = object : Parcelable.Creator<Joke> {
      override fun createFromParcel(parcel: Parcel): Joke = Joke(parcel)

      override fun newArray(size: Int): Array<Joke?> = arrayOfNulls(size)
    }
  }
}