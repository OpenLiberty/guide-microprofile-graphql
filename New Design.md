# App used

- Use System/Inventory App many other guides use
- Can query for any number of systems, and any properties within

# What you'll Learn

- Describe GraphQL
- GraphQL benefits 
  - Only querying for exactly what you need
  - Simple querying by fields
  - Query nested fields
- Describe GraphiQL used for in-browser testing
- Schema:

```
type Inventory {
  size: Int!
  systems: [System!]
}

type System {
  hostname: String!
  systemLoad: Float!
  note: String
}

type Query {
  inventory: Inventory
  system(hostname: String!): System
}

type Mutation {
  addNote(note: NoteInput): Boolean!
}

type NoteInput {
  hostname: String!
  note: String
}
```

- Example input/outputs:

```
query {
  Inventory {
    size
  }
}

{
  "data" : {
    "inventory": {
      "size": 3
    }
  }
}
```

```
query {
  inventory {
    size
    systems {
      hostname
      systemLoad
    }
  }
}

{
  "data" : {
    "inventory": {
      "size": 2
      "systems": [
        {
          "hostname": "system-1"
          "systemLoad": 5
        },
        {
          "hostname": "system-2"
          "systemLoad": 7
        }
      ]
    }
  }
}
```

# Building Application

- Same as before
  - Start in devmode
  - Modify `server.xml`

# Creating GraphQL API

- Similar to before
- Do with System/Inventory app
  - Instead of reading from JSON, will make calls to InventoryManager
  - Show off mutate by letting user add a "notes" field (?)

# Running Application

- Similar to before, access GraphiQL
- Make some queries and mutations
  - Basic query
  - Same query with additional fields
  - Mutation
  - Query for object after mutation
  

