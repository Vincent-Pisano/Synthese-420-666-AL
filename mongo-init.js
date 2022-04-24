db.auth("admin-user", "admin-password");

db = db.getSiblingDB("synthese");

// mongo -u admin -p --authenticationDatabase synthese
db.createUser({
  user: "admin",
  pwd: "admin123",
  roles: [
    {
      role: "readWrite",
      db: "synthese",
    },
  ],
});

db.synthese.insert({
  firstName: "Simon",
  lastName: "Kchouderian",
  email: "admin@admin.com",
  password: "admin123",
  creationDate: { $date: { $numberLong: "1644703495164" } },
  isDisabled: false,
  _class: "com.synthese.auth.model.Client",
});



