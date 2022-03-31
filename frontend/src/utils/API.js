
const URL_AUTH = "http://localhost:7873/";
const URL_ORDER = "http://localhost:7871/";
const URL_INVENTORY = "http://localhost:7870/";
//const URL_SHIPPING = "http://localhost:7872/";

// AUTH
export const SUBSCRIBE_CLIENT = URL_AUTH + "signUp"
export const LOGIN_CLIENT = URL_AUTH + "login/client/"
export const LOGIN_ADMIN = URL_AUTH + "login/admin/"

//INVENTORY
export const SAVE_ITEM = URL_INVENTORY + "add/item"
export const GET_ALL_ITEMS_FROM_CATEGORY = URL_INVENTORY + "get/all/items/category/"
export const GET_VISIBLE_ITEMS_FROM_CATEGORY = URL_INVENTORY + "get/visible/items/category/"
export const GET_ITEM_IMAGE = URL_INVENTORY + "get/image/"

//ORDER
export const SAVE_ORDER = URL_ORDER + "save/order"
export const API_CONFIRM_ORDER = URL_ORDER + "confirm/order"
export const GET_WAITING_ORDER = URL_ORDER + "get/order/waiting/"
export const GET_ALL_ORDERS_OF_CLIENT = URL_ORDER + "get/all/orders/"