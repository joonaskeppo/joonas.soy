const colors = require("tailwindcss/colors");
const aspectRatio = require("@tailwindcss/aspect-ratio");

module.exports = {
  purge: ["./src/**/*.clj", "./src/**/*.html"],
  theme: {
    extend: {
      colors,
      screens: {
        print: { raw: "print" },
      },
      fontFamily: {
        sans: [
          "Work Sans",
          "ui-sans-serif",
          "system-ui",
          "-apple-system",
          "BlinkMacSystemFont",
          "Segoe UI",
          "Roboto",
          "Helvetica Neue",
          "Arial",
          "Noto Sans",
          "sans-serif",
          "Apple Color Emoji",
          "Segoe UI Emoji",
          "Segoe UI Symbol",
          "Noto Color Emoji",
        ],
        mono: [
          "Space Mono",
          "ui-monospace",
          "SFMono-Regular",
          "Consolas",
          "Liberation Mono",
          "monospace",
        ],
      },
    },
  },
  variants: {},
  plugins: [aspectRatio],
};
