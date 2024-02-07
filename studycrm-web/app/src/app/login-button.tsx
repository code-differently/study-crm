'use client'

import Image from 'next/image'
import { signIn } from 'next-auth/react'

export default function LoginButton() {
  return (
    <p>
        <a href="#" onClick={() => signIn('studycrm')}>Login</a>
    </p>
  )
}
