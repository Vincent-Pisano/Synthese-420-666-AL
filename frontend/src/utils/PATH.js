import Home from '../components/Home';
import Login from '../components/auth/Login';
import Subscribe from '../components/auth/Subscribe';
import AddItemForm from '../components/addItem/AddItemForm';
import {
    URL_HOME,
    URL_LOGIN,
    URL_SUBSCRIBE,
    URL_CONFIRM_ORDER,
    URL_ADD_ITEM
} from "./URL.js"
import Auth from '../services/Auth';
import ConfirmOrder from '../components/confirmOrder/ConfirmOrder';

export const OPTIONS_ADMIN = [
    {
        link: URL_ADD_ITEM,
        title: "Ajouter un produit"
    }
]

export const ROUTES = [
    {
        link : URL_HOME,
        component: Home,
        accessValid: true
    },
    {
        link : URL_LOGIN,
        component: Login,
        accessValid: true
    },
    {
        link : URL_SUBSCRIBE,
        component: Subscribe,
        accessValid: Auth.isClient()
    },
    {
        link : URL_CONFIRM_ORDER,
        component: ConfirmOrder,
        accessValid: Auth.isClient()
    },
    {
        link : URL_ADD_ITEM,
        component: AddItemForm,
        accessValid: Auth.isAdministrator()
    }
]