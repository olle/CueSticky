package extra;

public class XThread extends Thread {

  public static boolean delay(long millis) {
    if (interrupted()) 
      return false;
    try {
        sleep(millis);
    }
    catch (InterruptedException e) {
        return false;
    }
    return true;
  }

  public static boolean delay(long millis, int nanos) {
    if (interrupted()) 
      return false;
    try {
        sleep(millis, nanos);
    }
    catch (InterruptedException e) {
        return false;
    }
    return true;
  }

}

