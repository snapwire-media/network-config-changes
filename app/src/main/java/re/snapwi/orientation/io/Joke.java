package re.snapwi.orientation.io;

import android.os.Parcel;
import android.os.Parcelable;

public class Joke implements Parcelable {
  private Value value;

  public String getJoke() {
    return value.getJoke();
  }

  protected Joke(Parcel in) {
    value = (Value) in.readValue(Value.class.getClassLoader());
  }

  @Override public int describeContents() {
    return 0;
  }

  @Override public void writeToParcel(Parcel dest, int flags) {
    dest.writeValue(value);
  }

  @SuppressWarnings("unused")
  public static final Parcelable.Creator<Joke> CREATOR = new Parcelable.Creator<Joke>() {
    @Override public Joke createFromParcel(Parcel in) {
      return new Joke(in);
    }

    @Override public Joke[] newArray(int size) {
      return new Joke[size];
    }
  };
}