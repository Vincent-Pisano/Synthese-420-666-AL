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

db.admin.insert({
  firstName: "Simon",
  lastName: "Kchouderian",
  email: "admin@admin.com",
  password: "admin123",
  creationDate: new Date("2022-02-12T22:04:55.164+00:00"),
  isDisabled: false,
  _class: "com.synthese.auth.model.Client",
});



