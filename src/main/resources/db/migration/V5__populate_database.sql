-- Insert categories
INSERT INTO categories (name)
VALUES ('Fruits & Vegetables'),
       ('Dairy & Eggs'),
       ('Bakery'),
       ('Meat & Seafood'),
       ('Pantry Staples'),
       ('Beverages'),
       ('Frozen Foods'),
       ('Snacks');

-- Insert products
INSERT INTO products (name, price, description, category_id)
VALUES
-- Fruits & Vegetables
('Red Delicious Apples', 2.99, 'Sweet, crisp apples perfect for snacking or baking', 1),
('Organic Baby Spinach', 3.49, 'Fresh organic baby spinach leaves, pre-washed', 1),

-- Dairy & Eggs
('Whole Milk 1 Gallon', 3.89, 'Fresh whole milk, pasteurized and homogenized', 2),
('Large Grade A Eggs (12 count)', 4.29, 'Farm fresh large eggs', 2),

-- Bakery
('Whole Wheat Bread', 2.99, 'Freshly baked whole wheat bread, 20 oz loaf', 3),
('Chocolate Chip Cookies', 3.49, 'Soft baked cookies with semi-sweet chocolate chips', 3),

-- Meat & Seafood
('Boneless Skinless Chicken Breasts', 5.99, 'Fresh never frozen chicken breasts, 1 lb package', 4),
('Fresh Atlantic Salmon Fillet', 9.99, 'Wild-caught salmon fillet, approximately 1 lb', 4),

-- Pantry Staples
('Extra Virgin Olive Oil 16oz', 8.99, 'Premium imported olive oil, cold pressed', 5),
('Organic Quinoa 16oz', 5.49, 'Nutritious organic quinoa, perfect for salads and sides', 5),

-- Beverages
('Sparkling Mineral Water 1L', 1.99, 'Naturally carbonated mineral water', 6),
('Premium Ground Coffee 12oz', 6.99, 'Dark roast ground coffee with rich flavor', 6),

-- Frozen Foods
('Mixed Berry Blend 16oz', 3.99, 'Frozen strawberries, blueberries, raspberries and blackberries', 7),
('Vegetable Pizza 12"', 4.99, 'Frozen cheese pizza with bell peppers, mushrooms and onions', 7),

-- Snacks
('Sea Salt Potato Chips 8oz', 2.99, 'Classic crispy potato chips with sea salt', 8),
('Dark Chocolate Almonds 6oz', 4.49, 'Roasted almonds covered in rich dark chocolate', 8);