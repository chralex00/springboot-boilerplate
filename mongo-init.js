db.createUser(
    {
        user: "username",
        pwd: "password",
        roles: [
            {
                role: "readWrite",
                db: "segment"
            }
        ]
    }
);

db.getSiblingDB("segment");

db.createCollection("muscles");
db.createCollection("foods");
db.createCollection("exercises");
db.createCollection("activities");
db.createCollection("diets");
db.createCollection("trainings");
db.createCollection("tdees");
