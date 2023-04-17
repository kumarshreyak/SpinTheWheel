const fs = require('fs');
const path = require('path');
const { MongoClient, ServerApiVersion } = require('mongodb');

// MongoDB connection URI
const uri = "mongodb+srv://kumarshreyak:@cluster0.shkttin.mongodb.net/?retryWrites=true&w=majority";

// Create a MongoClient with a MongoClientOptions object to set the Stable API version
const client = new MongoClient(uri, {
  serverApi: {
    version: ServerApiVersion.v1,
    strict: true,
    deprecationErrors: true,
  }
});

async function insertDocuments(client, dbName, collName, documents) {
  const db = client.db(dbName);
  const coll = db.collection(collName);
  const result = await coll.insertMany(documents);
  console.log(`${result.insertedCount} documents were inserted into ${collName} collection`);
}

async function readJsonFile(filePath) {
  return new Promise((resolve, reject) => {
    fs.readFile(filePath, 'utf8', (err, data) => {
      if (err) {
        reject(err);
      } else {
        try {
          resolve(JSON.parse(data));
        } catch (e) {
          console.error(`Error parsing JSON file ${filePath}:`, e);
          resolve({});
        }
      }
    });
  });
}


async function run() {
  try {
    // Connect the client to the MongoDB server
    await client.connect();

    // List the databases on the server
    const databases = await client.db().admin().listDatabases();

    console.log("Connected to MongoDB server:");
    console.log("  Databases:");
    databases.databases.forEach(db => {
      console.log(`    ${db.name}`);
    });

    // Insert documents from JSON files in the folder
    const folderPath = "/Users/shreyak/Downloads/t20s_male_json";
    const fileNames = fs.readdirSync(folderPath);
    const documents = [];
    for (const fileName of fileNames) {
      const filePath = path.join(folderPath, fileName);
      const fileContent = await readJsonFile(filePath);
      documents.push(fileContent);
    }
    await insertDocuments(client, "CricketDB", "Matches", documents);
  } finally {
    // Close the MongoDB client connection
    await client.close();
  }
}

run().catch(console.dir);
