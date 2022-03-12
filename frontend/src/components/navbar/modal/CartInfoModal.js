import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faTrashAlt } from "@fortawesome/free-solid-svg-icons";
import { GET_ITEM_IMAGE } from "../../../utils/API";

const CartInfoModal = ({ show, handleClose, cart, setCart }) => {

  function updateQuantityOrderItem(event) {
    let indexOrderItem = cart.orderItems.findIndex(
      (orderItem) => orderItem.item.name === event.target.getAttribute("name")
    );
    let oldOrderItems = cart.orderItems;
    oldOrderItems[indexOrderItem].quantity = parseInt(event.target.value);
    return oldOrderItems;
  }

  function updateOrder(event) {
    let newOrderItem = event.target.id === "update"
    ? updateQuantityOrderItem(event)
    : cart.orderItems.filter(
        (orderItem) =>
          orderItem.item.name !== event.target.getAttribute("name")
      );
    setCart((oldCart) => ({
      client: oldCart.client,
      status: oldCart.status,
      orderItems: newOrderItem,
      totalPrice: newOrderItem
        .map((orderItem) => orderItem.item.price * orderItem.quantity)
        .reduce((prev, curr) => prev + curr, 0),
    }));
  }

  function sum() {
    console.log();
  }

  sum();

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
            <div className="modal-dialog modal-lg">
              <div
                className="modal-content bg-dark text-white"
                style={{ boxShadow: "3px 3px 4px rgba(0,0,0,0.4)" }}
              >
                <div className="modal-body form-dark" style={{ margin: "0%" }}>
                  <div className="modal-header border-bottom-0">
                    <h2 className="modal-title" id="exampleModalLabel">
                      Votre Panier
                    </h2>
                  </div>
                  <div className="modal-body">
                    <table className="table table-image text-white">
                      <thead>
                        <tr>
                          <th scope="col"></th>
                          <th scope="col">Produit</th>
                          <th scope="col">Prix</th>
                          <th scope="col">Quantité</th>
                          <th scope="col">Total</th>
                          <th scope="col">Actions</th>
                        </tr>
                      </thead>
                      <tbody className="text-center">
                        {cart.orderItems.length > 0 ? (
                          cart.orderItems.map((orderItem, i) => (
                            <tr key={i}>
                              <td className="w-25 align-middle">
                                <img
                                  src={GET_ITEM_IMAGE + orderItem.item.id}
                                  className="img-fluid img-thumbnail"
                                  alt="produit"
                                />
                              </td>
                              <td className="align-middle">
                                {orderItem.item.name}
                              </td>
                              <td className="align-middle">
                                {orderItem.item.price}$
                              </td>
                              <td className="align-middle qty">
                                <input
                                  style={{ maxWidth: "3.5rem" }}
                                  type="number"
                                  className="form-control"
                                  min="1"
                                  step="1"
                                  name={orderItem.item.name}
                                  id="update"
                                  value={orderItem.quantity}
                                  onChange={(event) => updateOrder(event)}
                                />
                              </td>
                              <td className="align-middle">
                                {orderItem.item.price * orderItem.quantity}$
                              </td>
                              <td className="align-middle">
                                <button
                                  className="btn btn-danger"
                                  name={orderItem.item.name}
                                  id="delete"
                                  onClick={(event) => updateOrder(event)}
                                >
                                  <FontAwesomeIcon
                                    className="text-white"
                                    icon={faTrashAlt}
                                  />
                                </button>
                              </td>
                            </tr>
                          ))
                        ) : (
                          <tr>
                            <td colSpan={6}>
                              <div
                                className="alert alert-danger mt-3"
                                role="alert"
                              >
                                Aucun produit n'est enregristré dans le panier
                              </div>
                            </td>
                          </tr>
                        )}
                      </tbody>
                    </table>
                    <div className="d-flex justify-content-end">
                      <h5>
                        Total:{" "}
                        <span className="price text-success">
                          {cart.totalPrice}$
                        </span>
                      </h5>
                    </div>
                  </div>
                  <div className="modal-footer border-top-0 d-flex justify-content-between">
                    <button
                      className="mx-2 btn btn-outline-danger text-right"
                      onClick={() => handleClose()}
                    >
                      <i className="bi-cart-fill me-1"></i>
                      fermer
                    </button>
                    <button type="button" className="btn btn-success">
                      Confirmer la commande
                    </button>
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
export default CartInfoModal;
