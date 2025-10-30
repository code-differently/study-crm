'use client';

import { useSession } from 'next-auth/react';

/**
 * Simple hook that provides session info
 * Note: Middleware handles most auth errors and redirects now
 */
export function useAuthenticatedSession() {
  const { data: session, status } = useSession();

  return {
    session,
    isAuthenticated: !!session?.user && !session?.error,
    isLoading: status === 'loading',
    hasError: !!session?.error,
  };
}
