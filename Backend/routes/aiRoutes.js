import express from "express";
import { searchRoomsWithAI } from "../controllers/aiController.js";

const router = express.Router();

router.post("/search", searchRoomsWithAI);

export default router;