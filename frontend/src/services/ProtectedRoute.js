import React from "react";
import { Navigate, Outlet } from "react-router-dom";
import {URL_HOME} from "../utils/URL"

export const ProtectedRoute = () => {
    const auth = true; // à changer

    return auth ? <Outlet /> : <Navigate to={URL_HOME} />;
}