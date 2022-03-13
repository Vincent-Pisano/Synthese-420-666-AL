import { useState, useContext } from "react";
import { CartContext } from "../../../contexts/CartContext";
import { GET_ITEM_IMAGE, SAVE_ORDER } from "../../../utils/API";
import {
  CONFIRM_ADD_ITEM_TO_CART,
  ERROR_QUANTITY_INVALID,
  ERROR_UPDATE_CART,
} from "../../../utils/MESSAGE";
import axios from "axios";

const ItemInfoClientModal = ({ handleClose, show, currentItem }) => {
  const [quantity, setQuantity] = useState(0);
  // eslint-disable-next-line no-unused-vars
  const [cart, setCart] = useContext(CartContext);
  const [errorMessage, setErrorMessage] = useState("");

  function updateQuantityOrderItem(indexOrderItem) {
    let oldOrderItems = cart.orderItems;
    oldOrderItems[indexOrderItem].quantity += parseInt(quantity);
    return oldOrderItems;
  }

  function addItemToCart() {
    if (quantity > 0) {
      let indexOrderItem = cart.orderItems.findIndex(
        (orderItem) => orderItem.item.id === currentItem.id
      );

      let newOrderItems =
        indexOrderItem !== -1
          ? updateQuantityOrderItem(indexOrderItem)
          : [
              ...cart.orderItems,
              {
                item: currentItem,
                quantity: parseInt(quantity),
              },
            ];

      let fields = {
        id: cart.id,
        client: cart.client,
        status: cart.status,
        orderItems: newOrderItems,
        totalPrice: newOrderItems
          .map((orderItem) => orderItem.item.price * orderItem.quantity)
          .reduce((prev, curr) => prev + curr, 0),
        creationDate: cart.creationDate,
        isDisabled: cart.isDisabled,
      };
      axios
        .post(SAVE_ORDER, fields)
        .then((response) => {
          setCart(response.data);
          setTimeout(() => {
            setQuantity(0);
            setErrorMessage("");
            handleClose();
          }, 3000);
          setErrorMessage(CONFIRM_ADD_ITEM_TO_CART);
        })
        .catch((error) => {
          setErrorMessage(ERROR_UPDATE_CART);
        });
    } else {
      setErrorMessage(ERROR_QUANTITY_INVALID);
    }
  }

  function showErrorMessage() {
    if (errorMessage !== "") {
      return (
        <div
          className={
            errorMessage === CONFIRM_ADD_ITEM_TO_CART
              ? "alert alert-success"
              : "alert alert-danger"
          }
          role="alert"
        >
          {errorMessage}
        </div>
      );
    }
  }

  function showModal() {
    if (show) {
      return (
        <>
          <div className="fade modal-backdrop show"></div>
          <div
            role="dialog"
            aria-modal="true"
            className="fade modal show"
            tabIndex="-1"
            style={{ display: "block" }}
          >
            <div className="modal-dialog modal-dialog-centered ">
              <div
                className="modal-content bg-dark text-white"
                style={{ boxShadow: "3px 3px 4px rgba(0,0,0,0.4)" }}
              >
                <div className="modal-body form-dark" style={{ margin: "0%" }}>
                  <img
                    style={{ height: "100%", width: "100%" }}
                    alt="Produit"
                    src={GET_ITEM_IMAGE + currentItem.id}
                  />
                  <div>
                    <h2 className="text-center my-3">{currentItem.name}</h2>
                    <p>{currentItem.description}</p>
                    <hr />
                    <h5 className="text-center my-3">
                      {currentItem.onSale ? (
                        <>
                          <span className="text-muted text-decoration-line-through">
                            ${currentItem.beforeSalePrice.toFixed(2)}
                          </span>
                          -
                        </>
                      ) : (
                        <></>
                      )}
                      ${currentItem.price.toFixed(2)}
                    </h5>
                    <p className="text-center my-3">
                      {currentItem.quantity > 25
                        ? `Encore ${currentItem.quantity} en stock !`
                        : currentItem.quantity > 0
                        ? `Attention, plus que ${currentItem.quantity} en stock !`
                        : "Plus aucun Stock pour ce produit..."}
                    </p>
                  </div>
                  {showErrorMessage()}
                  <div className="d-flex justify-content-space">
                    <button
                      className="mx-2 btn btn-outline-danger text-right"
                      onClick={() => {
                        setQuantity(0);
                        setErrorMessage("");
                        handleClose();
                      }}
                    >
                      <i className="bi-cart-fill me-1"></i>
                      fermer
                    </button>
                    <div
                      style={{
                        display: "table",
                        width: "55%",
                        marginLeft: "28%",
                      }}
                    >
                      <div style={{ display: "table-cell", width: "30%" }}>
                        <input
                          type="number"
                          min="0"
                          max={currentItem.quantity}
                          step="1"
                          value={quantity}
                          onChange={(event) => setQuantity(event.target.value)}
                          className="form-control"
                          id="quantity"
                        />
                      </div>
                      <button
                        className="mx-2 btn btn-outline-warning text-right"
                        disabled={currentItem.quantity <= 0}
                        onClick={() => addItemToCart()}
                      >
                        <i className="bi-cart-fill me-1"></i>
                        Ajouter au Panier
                      </button>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </>
      );
    }
  }

  return <>{showModal()}</>;
};
export default ItemInfoClientModal;
