# Schema

```
type System {
    os: OS
    java: Java
    timezone: String
    username: String
    note: String
}

type OS {
    arch: String
    name: String
    version: String
}

type Java {
    vendor: String
    version: String
}

type Query {
    system: System
}

type Mutation {
    editNote(note: String!) : Boolean!
}
```

# Example input/output

```
query {
    system {
        username
        note
    }
}

{
    "data": {
        "system": {
            "username": "user",
            "note": "test note"
        }
    }
}
```

```
query {
    system {
        username
        java {
            vendor
            version
        }
        os {
            arch
            name
        }
    }
}

{
    "data": {
        "system": {
            "username": "user",
            "java": {
                "version": "13.0.2",
                "vendor": "OpenJDK"
            },
            "os": {
                "arch": "x86_64",
                "name": "Mac OS X"
            }
        }
    }
}
```

# README breakdown
