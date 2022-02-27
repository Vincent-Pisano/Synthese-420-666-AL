import { useState } from "react";

export function useFormFields(initialState) {
  const [fields, setValues] = useState(initialState);

  return [
    fields,
    function (event) {
      if (event.target.type === "checkbox") {
        setValues({
          ...fields,
          [event.currentTarget.id]: event.target.checked,
        });
      }
      else {
        setValues({
          ...fields,
          [event.currentTarget.id]: event.target.value,
        });
      }
      
    },
  ];
}
