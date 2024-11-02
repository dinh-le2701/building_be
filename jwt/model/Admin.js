const Account = require('./Account');
const mongoose = require("mongoose")

const adminSchema = new mongoose.Schema({
  admin_id: { type: String, unique: true },
});

module.exports = Account.discriminator('admin', adminSchema);