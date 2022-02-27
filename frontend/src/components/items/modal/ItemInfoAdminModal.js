import AddItemForm from "../../addItem/AddItemForm";

const ItemInfoAdminModal = ({ handleClose, show, currentItem }) => {
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
                  <AddItemForm isModal item={currentItem} handleClose={handleClose}/>
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
        </>
      );
    }
  }

  return <>{showModal()}</>;
};
export default ItemInfoAdminModal;
