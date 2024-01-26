/** @type {import('next').NextConfig} */
const nextConfig = {
    logging: {
      level: 'verbose',
      fetches: {
        fullUrl: true,
      },
    },
}

module.exports = nextConfig
