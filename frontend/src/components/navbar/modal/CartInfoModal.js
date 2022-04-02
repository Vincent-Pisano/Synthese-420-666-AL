import { useNavigate } from "react-router";
import { URL_CONFIRM_ORDER } from "../../../utils/URL";
import CartTable from "../CartTable";

const CartInfoModal = ({ show, handleClose, cart, setCart }) => {

  let navigate = useNavigate();

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
                    <CartTable cart={cart} setCart={setCart} />
                  </div>
                  <div className="modal-footer border-top-0 d-flex justify-content-between">
                    <button
                      className="mx-2 btn btn-outline-danger text-right"
                      onClick={() => handleClose()}
                    >
                      <i className="bi-cart-fill me-1"></i>
                      fermer
                    </button>
                    <button type="button" className="btn btn-success"
                    disabled={cart.orderItems === undefined || cart.orderItems.length === 0 || cart.totalPrice <= 0}
                    onClick={() => {
                      handleClose();
                      navigate(URL_CONFIRM_ORDER);
                    }}>
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
