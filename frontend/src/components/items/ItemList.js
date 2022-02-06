import React from "react";
import Item from "./Item";

const ItemList = () => {

    // temp / plus tard : ajouter image et logique pour note (sur 5)
    const ITEM_LIST = [
        {
            name:"product1",
            description:"non",
            currentPrice:50.00,
            isOnSale:false,
            salePrice:undefined
        },
        {
            name:"product2",
            description:"non",
            currentPrice:80.00,
            isOnSale:true,
            oldPrice:99.99
        },
        {
            name:"product3",
            description:"non",
            currentPrice:25.50,
            isOnSale:false,
            oldPrice:undefined
        },
        {
            name:"product4",
            description:"non",
            currentPrice:30.00,
            isOnSale:true,
            oldPrice:42.00
        },
    ]

  return (
    <section className="py-5">
      <div className="container px-4 px-lg-5 mt-5">
        <div className="row gx-4 gx-lg-5 row-cols-2 row-cols-md-3 row-cols-xl-4 justify-content-center">
            {ITEM_LIST.map((item, i) => (
                <Item key={i} item={item}/>
            ))}
        </div>
      </div>
    </section>
  );
};
export default ItemList;
