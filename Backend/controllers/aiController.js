import { parseRoomSearch } from "../services/aiService.js";
import Room from "../models/Room.js";

export const searchRoomsWithAI = async (req, res) => {
  try {
    const { message } = req.body;

    if (!message) {
      return res.status(400).json({
        success: false,
        message: "Search message is required"
      });
    }

    // 1. Ask AI to understand the user's request
    const aiResult = await parseRoomSearch(message);

    // 2. Convert AI response from string to JSON
    const searchCriteria = JSON.parse(aiResult);

    console.log("AI Search Criteria:", searchCriteria);

    // 3. Build MongoDB filter
    const filter = {
      isAvailable: true
    };

    if (searchCriteria.roomType) {
      filter.type = {
        $regex: searchCriteria.roomType,
        $options: "i"
      };
    }

    if (searchCriteria.minPrice !== null) {
      filter.price = {
        ...filter.price,
        $gte: searchCriteria.minPrice
      };
    }

    if (searchCriteria.maxPrice !== null) {
      filter.price = {
        ...filter.price,
        $lte: searchCriteria.maxPrice
      };
    }

    // 4. Search MongoDB
    const rooms = await Room.find(filter);

    // 5. Return matching rooms
    res.json({
      success: true,
      criteria: searchCriteria,
      rooms
    });

  } catch (error) {
    console.error("AI Search Error:", error);

    res.status(500).json({
      success: false,
      message: "AI search failed",
      error: error.message
    });
  }
};