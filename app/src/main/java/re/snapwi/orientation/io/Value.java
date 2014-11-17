package re.snapwi.orientation.io;

import android.os.Parcel;
import android.os.Parcelable;

public class Value implements Parcelable {
  String joke;
  String id;

  public String getJoke() {
    return joke;
  }

  public String getId() {
    return id;
  }

  Value(Parcel in) {
    joke = in.readString();
    id = in.readString();
  }

  @Override public int describeContents() {
    return 0;
  }

  @Override public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(joke);
    dest.writeString(id);
  }

  @SuppressWarnings("unused") public static final Creator<Value> CREATOR = new Creator<Value>() {
    @Override public Value createFromParcel(Parcel in) {
      return new Value(in);
    }

    @Override public Value[] newArray(int size) {
      return new Value[size];
    }
  };
}
