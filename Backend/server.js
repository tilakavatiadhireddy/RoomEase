import express from "express";
import mongoose from "mongoose";
import cors from "cors";
import dotenv from "dotenv";
import roomRoutes from "./routes/roomRoutes.js";
import bookingRoutes from "./routes/bookingRoutes.js";
import userRoutes from "./routes/userRoutes.js";  
import aiRoutes from "./routes/aiRoutes.js";
import "dotenv/config";

const app = express();

app.use(cors());
app.use(express.json());
//need to check this
app.use("/uploads", express.static("uploads"));
app.use(
  cors({
    origin: "https://room-ease-e7iq.vercel.app",
    credentials: true,
  })
);
// Routes
app.use("/api/rooms", roomRoutes);
app.use("/api/bookings", bookingRoutes);
app.use("/api/users", userRoutes);
app.use("/api/ai", aiRoutes);
// Connect MongoDB
mongoose.connect(process.env.MONGO_URI)
  .then(() => console.log("MongoDB Connected"))
  .catch(err => console.log(err));

// Start Server
const PORT = process.env.PORT || 5004;
app.listen(PORT, () => console.log(`Server running on port ${PORT}`));
