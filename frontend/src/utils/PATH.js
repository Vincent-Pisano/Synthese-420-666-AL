import Home from '../components/Home';
import Login from '../components/auth/Login';
import Subscribe from '../components/auth/Subscribe';
import {
    URL_HOME,
    URL_LOGIN,
    URL_SUBSCRIBE
} from "./URL.js"

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
        accessValid: true
    }
]