const express = require('express');
const cors = require('cors');
const path = require('path');

const app = express();
const PORT = process.env.PORT || 8081;

app.use(cors({ origin: true, credentials: true }));
app.use(express.static(path.join(__dirname, '../public')));

app.get('/', (_req, res) => {
  res.redirect('/documentation.html');
});

app.get('/health', (_req, res) => {
  res.json({ status: 'ok' });
});

app.listen(PORT, () => {
  console.log(`API Docs server running at http://localhost:${PORT}`);
});
