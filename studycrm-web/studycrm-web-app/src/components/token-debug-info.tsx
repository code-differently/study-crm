'use client';

import { useSession } from 'next-auth/react';
import { useState, useEffect } from 'react';

export function TokenDebugInfo() {
  const { data: session, status } = useSession();
  const [currentTime, setCurrentTime] = useState(Date.now());

  useEffect(() => {
    const interval = setInterval(() => {
      setCurrentTime(Date.now());
    }, 1000);

    return () => clearInterval(interval);
  }, []);

  if (status === 'loading') {
    return <div className="text-sm text-gray-500">Loading session...</div>;
  }

  if (status === 'unauthenticated') {
    return <div className="text-sm text-red-500">Not authenticated</div>;
  }

  // Calculate token expiration info (assuming we have the JWT)
  const tokenExpiresAt = session?.accessToken
    ? decodeJWTExpiration(session.accessToken)
    : null;
  const timeToExpiry = tokenExpiresAt ? tokenExpiresAt - currentTime : null;
  const isExpired = timeToExpiry ? timeToExpiry <= 0 : false;
  const willExpireSoon = timeToExpiry ? timeToExpiry <= 5 * 60 * 1000 : false; // 5 minutes

  return (
    <div className="fixed bottom-4 right-4 bg-white border border-gray-300 rounded-lg p-4 shadow-lg max-w-sm">
      <h3 className="font-semibold text-sm mb-2">Token Debug Info</h3>
      <div className="space-y-1 text-xs">
        <div>
          <strong>Status:</strong> {status}
        </div>
        <div>
          <strong>User:</strong> {session?.user?.email || 'N/A'}
        </div>
        <div>
          <strong>Has Access Token:</strong>{' '}
          {session?.accessToken ? 'Yes' : 'No'}
        </div>
        <div>
          <strong>Session Error:</strong> {session?.error || 'None'}
        </div>
        {tokenExpiresAt && (
          <>
            <div>
              <strong>Token Expires:</strong>{' '}
              {new Date(tokenExpiresAt).toLocaleTimeString()}
            </div>
            <div
              className={`${isExpired ? 'text-red-600' : willExpireSoon ? 'text-orange-600' : 'text-green-600'}`}
            >
              <strong>Status:</strong>{' '}
              {isExpired
                ? 'EXPIRED'
                : willExpireSoon
                  ? 'EXPIRES SOON'
                  : 'VALID'}
            </div>
            {timeToExpiry && !isExpired && (
              <div>
                <strong>Time to expiry:</strong>{' '}
                {Math.floor(timeToExpiry / 60000)}m{' '}
                {Math.floor((timeToExpiry % 60000) / 1000)}s
              </div>
            )}
          </>
        )}
        <div className="text-gray-500">
          Current: {new Date(currentTime).toLocaleTimeString()}
        </div>
      </div>
    </div>
  );
}

function decodeJWTExpiration(token: string): number | null {
  try {
    const payload = JSON.parse(atob(token.split('.')[1]));
    return payload.exp ? payload.exp * 1000 : null; // Convert to milliseconds
  } catch (error) {
    console.warn('Failed to decode JWT:', error);
    return null;
  }
}
