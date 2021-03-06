export const ERROR_EMAIL_ALREADY_EXISTS = "Un compte avec ce courriel existe déjà ..."
export const ERROR_ACCOUNT_NOT_FOUND = "Aucun compte trouvé avec ces identifiants ..."
export const ERROR_SERVER_NOT_FOUND = "La connexion au serveur n'a pas pu être effectuée ..."
export const CONFIRM_ADD_ITEM = "Le produit a été ajouté, redirection en cours"
export const CONFIRM_UPDATE_ITEM = "Le produit a été modifié, fermeture en cours"
export const ERROR_INVALID_ITEM = "Une erreur est survenue durant l'ajout du produit"
export const ERROR_NO_ITEM_FOUND_CATEGORY = "Aucun produit n'a été trouvé dans cette catégorie..."
export const ERROR_QUANTITY_INVALID = "Choisissez une quantité valide..."
export const ERROR_NOT_LOG_IN = "Veuillez vous connecter au préalable"
export const CONFIRM_ADD_ITEM_TO_CART = "Votre commande à été mise à jour"
export const ERROR_UPDATE_CART = "La mise à jour du panier à été compromise..."
export const ERROR_CONFIRM_ORDER = (items) => {
    return (items.length === 1 ? items.length + " produit n'a pas assez de stock pour valider la commande : " : items.length + " produits n'ont pas assez de stock pour valider la commande : ") 
    + items.map(item => 
        ` ${item.quantity} ${item.name} en stock`
      );
}
export const CONFIRM_ORDER = "Votre commande est confirmée ! Redirection en cours"
export const ERROR_ORDERS_NOT_FOUND = "Aucune commande trouvée ..."