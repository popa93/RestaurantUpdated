package Util;

public
class Constants {

    public static final String CREATE_DRINKS_TABLE = "CREATE TABLE IF NOT EXISTS 'Drinks' ( 'uuid' integer primary key autoincrement not null ,'image_link' TEXT,'item_name' TEXT,'price' integer,'ingredients' TEXT)";
    public static final String FOOD_TABLE_ADD_QUANTITY_COLUMN = "ALTER TABLE 'Food' ADD COLUMN quantity integer not null default 0";
    public static final String DRINKS_TABLE_ADD_QUANTITY_COLUMN = "ALTER TABLE 'Drinks' ADD COLUMN quantity integer not null default 0";
    public static final String DATABASE_NAME = "itemsDatabase";
    public static final String FOOD = "Food";
    public static final String DRINK = "Drink";
    public static final String DRINKS = "Drinks";
    public static final String ORDER = "Order";
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
    public static final String DATE_FORMAT = "dd/M//yyyy hh:mm:ss";
    public static final String DATE_FORMAT_DMY = "ddMyyyy";
    public static final String DATE_FORMAT_H = "hh:mm:ss";
    public static final String CLIENT_THEME = "client";
    public static final String KITCHEN_THEME = "kitchen";
    public static final String ADMIN_THEME = "admin";
    public static final String NO_THEME = "notheme";
    public static final String LEI = " lei";
    public static final String QUANTITY = "Quantity(ml)";
    public static final String ORDER_ERROR = "Something went wrong ordering, please report to restaurant manager";
    public static final String DEVICE_EMUI = "868757046395816"; //for more devices all emuis should be listen
    public static final String KITCHEN = "Kitchen";
    public static final String DATE = "date";
    public static final String TOTAL = "total";
    public static final String REMARKS = "remarks";
    public static final String FOOD_NODE = "food";
    public static final String DRINK_NODE = "drink";
    public static final String ORDER_STATUS = "orderStatus";
    public static final String TRUE = "true";
    public static final String EXTRA_INFO = "Extra info:";
    public static final String CACHE_TIME = "CacheTime";
    public static final String ADD_NEW_MENU_ITEM = "Add new menu item";
    public static long REFRESH_TIME = 5 * 1000 * 1000 * 1000L; // 5 sec(30 min) interval
    public static double LAT = 46.547890;
    public static double LNG = 24.568380;
    public static long DELAY_TIME = 1500;

}
