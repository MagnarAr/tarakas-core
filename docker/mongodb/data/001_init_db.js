db.createUser(
    {
        "user": "tarakas-user",
        "pwd": "tarakas-pw",
        "roles": [
            {
                "role": "readWrite",
                "db": "tarakas-db"
            }
        ]
    }
);