import CartItem from "../../navbar/CartItem";

const OrderInfoModal = ({ show, handleClose, currentOrder }) => {
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
                      Votre Commande
                    </h2>
                  </div>
                  <div className="modal-body">
                    <div className="table-responsive">
                      <table className="table table-image text-white">
                        <thead>
                          <tr>
                            <th scope="col"></th>
                            <th scope="col">Produit</th>
                            <th scope="col">Prix</th>
                            <th scope="col">Quantité</th>
                            <th scope="col">Total</th>
                          </tr>
                        </thead>
                        <tbody className="text-center">
                            {currentOrder.orderItems.map((orderItem, i) => (
                              <CartItem
                                key={i}
                                orderItem={orderItem}
                              />
                            )
                          )}
                        </tbody>
                      </table>
                      <div className="d-flex justify-content-end">
                        <h5>
                          Total:{" "}
                          <span className="price text-success">
                            {currentOrder.totalPrice.toFixed(2)}$
                          </span>
                        </h5>
                      </div>
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
export default OrderInfoModal;
