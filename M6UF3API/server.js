require('dotenv').config();
const express = require('express');
const mongoose = require('mongoose');
const app = express();
const port = process.env.PORT || 3021;
// Middleware per parsejar el cos de les sol·licituds a JSON
app.use(express.json());
app.use(express.urlencoded({ extended: true }));
// Connecta't a MongoDB (modifica l'URI amb la teva pròpia cadena de connexió)

//const uri = "mongodb+srv://agarci9:xxxx@cluster0.gc1mk.mongodb.net/albums?appName=Cluster0";
const uri = process.env.MONGO_URI;
console.log("URI: ", uri);

//const clientOptions = { serverApi: { version: '1', strict: true, deprecationErrors: true },  useNewUrlParser: true, useUnifiedTopology: true };
const clientOptions = {};

mongoose.connect(process.env.MONGO_URI)
  .then(() => console.log('Connected to MongoDB: Entrades'))
  .catch(err => console.log('Error connecting to MongoDB:', err));

// Definició del model de dades (un exemple simple d'un model de "Usuari")
const entradesSchema = new mongoose.Schema({
  cognom1: { type: String, required: true },
  cognom2: { type: String, required: true },
  completa: { type: Boolean, required: true },
  data_entrada: { type: String, required: true },
  nom_alumne: { type: String, required: true },
  observacions: { type: String, default: '' },
},
{
collection: 'Entrades',
timestamps: false,
}
);


const Entrades = mongoose.model('Tasques', entradesSchema);


/******************************************************** */
/******************************************************** */
/******************************************************** */
/******************************************************** */
/******************************************************** */
/**
 * END POINTS
 */

// Ruta a l'arrel
app.get('/', (req, res) => {
  res.send('Yout API is running!');
});

// Ruta per obtenir albums entre dates
app.get('/filterdates/:dataini/:datafi', async (req, res) => {
  try {
    const { dataini, datafi } = req.params;
    console.log("ENTRE DATES: ", dataini, datafi);
    const entrades = await Entrades.find({
      data_entrada: { $gte: dataini, $lte: datafi},
    });

    if (entrades.length === 0) {
      return res.status(404).json({ message: 'No entrada found in this date range' });
    }

    res.status(200).json(entrades);
  } catch (err) {
    res.status(500).json({ message: 'Error fetching entrada', error: err.message });
  }
});

// Ruta per obtenir tots els usuaris
app.get('/list', async (req, res) => {
  try {
    const entrades = await Entrades.find();
    res.status(200).json(entrades);
    console.log("working");
  } catch (err) {
    res.status(500).json({ message: 'Error fetching entrades', error: err.message });
  }
});

app.post('/add', async (req, res) => {
  const { cognom1, cognom2, completa, data_entrada, nom_alumne, observacions } = req.body;
  console.log("UPDATING ENTRADA: ", cognom1, cognom2, completa, data_entrada, nom_alumne, observacions);
  res.json({
    message: 'Entrada updated successfully',
    data: req.body
  });
});

/******************************************************** */
/******************************************************** */
/******************************************************** */
/******************************************************** */
/******************************************************** */
// changed
// Inicia el servidor
app.listen(port, '0.0.0.0', () => {
  console.log(`Server is running on http://localhost:${port}`);
});
