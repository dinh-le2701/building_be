const mongoose = require("mongoose")

const accountSchema = new mongoose.Schema({
    username: { type: String, require: true },
    email: { type: String, require: true, unique: true },
    password: { type: String, require: true },
    role: { type: String, require: true, enum: ['USER', 'ADMIN'] },

}, { discriminatorKey: "role", timestamps: true })

const Account = mongoose.model('Account', accountSchema);
module.exports = Account;