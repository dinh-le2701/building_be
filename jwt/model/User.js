const { default: mongoose } = require("mongoose")
const Account = require("./Account")

const userSchema = new mongoose.Schema({
    phone: String,
    address: String
})

module.exports = Account.discriminator('user', userSchema);