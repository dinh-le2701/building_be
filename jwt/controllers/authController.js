const bcrypt = require('bcryptjs');
const jwt = require('jsonwebtoken');
const Account = require('../model/Account'); // Đảm bảo đường dẫn này đúng với vị trí file Account.js

// Hàm đăng ký
const register = async (req, res) => {
    try {
        const { username, email, password, role } = req.body;

        // Kiểm tra nếu email đã tồn tại
        const existingAccount = await Account.findOne({ email });
        if (existingAccount) {
            return res.status(400).json({ message: 'Email already exists' });
        }

        // Mã hóa mật khẩu
        const hashedPassword = await bcrypt.hash(password, 10);

        // Tạo tài khoản mới
        const account = new Account({
            username,
            email,
            password: hashedPassword,
            role: role.toUpperCase() // Chuyển đổi vai trò thành chữ hoa
        });

        await account.save();
        res.status(201).json({ message: 'Account created successfully' });
    } catch (error) {
        res.status(500).json({ message: error.message });
    }
};

// Hàm đăng nhập
const login = async (req, res) => {
    try {
        const { email, password } = req.body;

        // Kiểm tra tài khoản
        const account = await Account.findOne({ email });
        if (!account) {
            return res.status(404).json({ message: 'Account not found' });
        }

        // So sánh mật khẩu
        const isMatch = await bcrypt.compare(password, account.password);
        if (!isMatch) {
            return res.status(400).json({ message: 'Invalid credentials' });
        }

        // Tạo JWT
        const token = jwt.sign(
            { accountId: account._id, role: account.role },
            process.env.JWT_SECRET,
            { expiresIn: '24h' } // Token hết hạn sau 24 giờ
        );

        const expireToken = 86400; // Thời gian hết hạn token tính bằng giây (24 giờ)

        // Trả về phản hồi đăng nhập
        res.json({
            token,
            expireToken,
            email: account.email,
            role: account.role
        });
    } catch (error) {
        res.status(500).json({ message: error.message });
    }
};

// Hàm đăng xuất
const logout = (req, res) => {
    // Ở phía server, bạn không cần làm gì đặc biệt cho logout. 
    // Bạn chỉ cần thông báo cho client để họ xóa token đã lưu.
    res.json({ message: 'Logged out successfully' });
};

module.exports = { register, login, logout };
