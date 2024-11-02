// server.js
require('dotenv').config();
const cors = require("cors")
const express = require('express');
const connectDB = require('./config/db');
const authRoutes = require('./routes/authRoutes');

const app = express();

// Middleware
app.use(cors()); // Kích hoạt CORS cho tất cả các yêu cầu
app.use(express.json());

// Connect to MongoDB
connectDB();

// Routes
app.use('/api/auth', authRoutes);

const PORT = process.env.PORT || 5000;
app.listen(PORT, () => {
  console.log(`Server running on port ${PORT}`);
});

// console.log(require('crypto').randomBytes(64).toString('hex'));