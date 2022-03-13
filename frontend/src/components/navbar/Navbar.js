import { React, useContext, useState } from "react";
import Auth from "../../services/Auth";
import { useNavigate } from "react-router";
import { URL_HOME, URL_LOGIN, URL_SUBSCRIBE } from "../../utils/URL";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faSearch } from "@fortawesome/free-solid-svg-icons";
import { OPTIONS_ADMIN } from "../../utils/PATH";
import { CartContext } from "../../contexts/CartContext";
import CartInfoModal from "./modal/CartInfoModal";

function Navbar() {
  let navigate = useNavigate();
  const [ cart, setCart ] = useContext(CartContext)

  const [show, setShow] = useState(false);
  const handleClose = () => setShow(false);
  const handleShow = () => setShow(true);

  function checkAuthentication() {
    return Auth.isAuthenticated() ? (
      <>
        {checkIfAdmin()}
        <button
          className="mx-2 btn btn-outline-danger text-right"
          onClick={() => Auth.logout(() => navigate(URL_LOGIN))}
        >
          <i className="bi-cart-fill me-1"></i>
          Déconnexion
        </button>
      </>
    ) : (
      <>
        <button
          className="mx-2 btn btn-outline-secondary text-right"
          onClick={() => navigate(URL_SUBSCRIBE)}
        >
          <i className="bi-cart-fill me-1"></i>
          Inscription
        </button>
        <button
          className="btn btn-outline-dark"
          onClick={() => navigate(URL_LOGIN)}
        >
          <i className="bi-cart-fill me-1"></i>
          Connexion
        </button>
      </>
    );
  }

  function checkIfAdmin() {
    if (Auth.isAdministrator()) {
      return (
        <div className="menu-item">
      <p className="menu-item-title">Options</p>
      <ul>
      {OPTIONS_ADMIN.map((options, i) => (
          <li key={i}>
            <button
              className="menu-item-button"
              onClick={() => navigate(options.link)}
            >
              {options.title}
            </button>
          </li>
        ))}
      </ul>
    </div>
      );
    } else {
      return (
        <button className="mx-2 btn btn-outline-dark" onClick={() => handleShow()}>
          <i className="bi-cart-fill me-1"></i>
          Cart
          <span className="badge bg-dark text-white ms-1 rounded-pill">{cart.orderItems !== undefined ? cart.orderItems.length : 0}</span>
        </button>
      );
    }
  }

  function showModal() {
    if (Auth.isAuthenticated() && !Auth.isAdministrator()) {
      return (
        <CartInfoModal
        show={show}
        handleClose={handleClose}
        cart={cart}
        setCart={setCart}
      />
      )
    } else {
      return (
        <></>
      )
    }
  }

  return (
    <nav className="navbar navbar-expand-lg navbar-light bg-light">
      <div className="container px-4 px-lg-5">
        <span
          className="navbar-brand"
          style={{ cursor: "pointer" }}
          onClick={() => navigate(URL_HOME)}
        >
          Synthese
        </span>
        <button
          className="navbar-toggler"
          type="button"
          data-bs-toggle="collapse"
          data-bs-target="#navbarSupportedContent"
          aria-controls="navbarSupportedContent"
          aria-expanded="false"
          aria-label="Toggle navigation"
        >
          <span className="navbar-toggler-icon"></span>
        </button>
        <div
          className="collapse navbar-collapse justify-content-between"
          id="navbarSupportedContent"
        >
          <div className="d-flex mx-2 mt-2 mt-lg-0">
            <form action="" method="get" id="search">
              <input
                type="text"
                name="search_text"
                id="search_text"
                placeholder="Rechercher"
                className="mr-2"
              />
              <button name="search_button" id="search_button">
                <FontAwesomeIcon className="text-white" icon={faSearch} />
              </button>
            </form>
          </div>
          <div className="d-flex justify-content-between mx-2 mt-3 mt-lg-0">
            {checkAuthentication()}
          </div>
        </div>
      </div>
      {showModal()}
    </nav>
  );
}

export default Navbar;
