import React from "react";
import ItemList from "./items/ItemList";
import Auth from "../services/Auth";

const Home = ({ history }) => {
  let user = Auth.user;

  return (
    <>
      <header className="bg-dark py-5">
        <div className="container px-4 px-lg-5 my-5">
          <div className="text-center text-white">
            <h1 className="display-4 fw-bolder">
              Bienvenue {user !== undefined ? user.firstName : ""}{" "}
              {user !== undefined ? user.lastName : ""}!
            </h1>
            <p className="lead fw-normal text-white-50 mb-0">
              {Auth.isAdministrator()
                ? "De nouveaux produit à rajouter ?"
                : "Prenez votre temps pour regarder notre catalogue"}
            </p>
          </div>
        </div>
      </header>
      <ItemList />
    </>
  );
};
export default Home;
