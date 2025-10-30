/** @type {import('next').NextConfig} */
const nextConfig = {
  // Allow development origins for local development
  allowedDevOrigins: ['local.studycrm.com'],
  images: {
    remotePatterns: [
      {
        protocol: 'https',
        hostname: 'avatars.githubusercontent.com'
      },
      {
        protocol: 'https',
        hostname: 'avatar.vercel.sh'
      }
    ]
  },
  // React Compiler configuration (moved out of experimental in Next.js 16)
  reactCompiler: false,
}

module.exports = nextConfig
