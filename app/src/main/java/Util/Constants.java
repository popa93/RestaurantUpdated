package Util;

public
class Constants {

    public static final String CREATE_DRINKS_TABLE="CREATE TABLE IF NOT EXISTS 'Drinks' ( 'uuid' integer primary key autoincrement not null ,'image_link' TEXT,'item_name' TEXT,'price' integer,'ingredients' TEXT)";
    public static final String FOOD_TABLE_ADD_QUANTITY_COLUMN="ALTER TABLE 'Food' ADD COLUMN quantity integer not null default 0";
    public static final String DRINKS_TABLE_ADD_QUANTITY_COLUMN="ALTER TABLE 'Drinks' ADD COLUMN quantity integer not null default 0";
    public static final String DATABASE_NAME="itemsDatabase";
    public static final String FOOD="Food";
    public static final String DRINK="Drink";
    public static final String DRINKS="Drinks";
    public static final String ORDER="Order";
    public static final String CHANNEL_ID = "Restaurant channel id";
    public static final String ZERO = "0";
    public static final String THANK_YOU = "Thank you ";
    public static final String EXCLAMATION = "!";
    public static final String ANNOUNCEMENT = "Yor order will be at your table in ";
    public static final String MIN = " min";
    public static final String CHANNEL_DESCRIPTION = "Order wait time channel";
    public static final String PREF_TIME = "Pref time";
    public static final String PREF_DATE = "Pref date";
    public static final String ACTION_BAR_DETAILS_TITLE = "Details";
    public static final String ACTION_BAR_MENU_TITLE = "Menu";
    public static final String ACTION_BAR_ORDER_TITLE = "Order";
    public static final String ACTION_BAR_REGISTER_TITLE = "Register";
    public static final String DETAILS_ITEM_KEY = "itemKey";
    public static final String EMPTY = "empty";
    public static final String EMPTY_FIELDS = "Email or password field is empty";
    public static final String OK = "Ok";
    public static final String OK2 = "OK";
    public static final String TASK_NOT_SUCCESSFUL = "Task is not successful";
    public static final String MAP_MARKER_TITLE = "Restaurant";
    public static final String ERROR_MSG = "Something went wrong!";
    public static final String FOUR = "4";
    public static final String EIGHT = "8";
    public static final String THIRTEEN = "13";
    public static final String EIGHTEEN = "18";
    public static final String COMPLETE_ALL_FIELDS = "All fields must be completed";
    public static final String IDENTICAL_PASSWORDS = "Password fields must be identical";
    public static final String EMAIL_BAD_FORMAT = "Email field bad format";
    public static final String PASSWORD_MIN_CHARACTERS = "Password must contain at least 6 characters";
    public static final String YES = "YES";
    public static final String NO = "NO";
    public static final String MENU = "Menu";
    public static final String ERROR = "Error";
    public static final String ORDERS = "Orders";
    public static final String EXCEPTION = "EXCEPTION";
    public static final String EMAIL_PATTERN = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    public static  long REFRESH_TIME = 5 * 1000 * 1000 * 1000L; // 5 sec(30 min) interval
    public static double  LAT = 46.547890;
    public static double  LNG = 24.568380;
    public static long  DELAY_TIME = 1500;

}
