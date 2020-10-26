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

## What you'll learn

- User will learn how to implement GraphQL feature on server 
- Describe the special way GraphQL makes queries in

## Building/Running App

- Standard TWYB
  - Make a query on all non-nested fields
  - Make a query on a nested field

## Enable GraphQL

- Start dev mode
- Show how user enables GraphQL in `server.xml` file
- Add GraphQL to `pom.xml`

## Creating Models

- Create model classes for the objects that will be returned
- Explain different annotations available
- Have user open schema page to see updated schema

## Creating API

- Create `SystemResource.java` class to handle requests
- Explain `@Query` and `@Mutation` annotation
  - Also explain the two kinds of queries

## Testing the application

- Mention how manual testing via GraphiQL is an option
- Describe benefits of automated tests
- Walkthrough of automated tests
