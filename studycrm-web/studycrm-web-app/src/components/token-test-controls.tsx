'use client';

import { signOut, signIn } from 'next-auth/react';
import { useState } from 'react';

export function TokenTestControls() {
  const [isLoading, setIsLoading] = useState(false);

  const forceTokenRefresh = async () => {
    setIsLoading(true);
    try {
      // Make a request that will trigger token refresh
      const response = await fetch('/api/graphql', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({
          query: `
            query {
              entities(type: "CONTACT") {
                id
              }
            }
          `,
        }),
      });

      const result = await response.json();
      console.log('GraphQL response:', result);

      if (response.status === 401) {
        console.log('Received 401 - token refresh likely failed');
      }
    } catch (error) {
      console.error('Error making test request:', error);
    } finally {
      setIsLoading(false);
    }
  };

  const clearSessionAndSignIn = async () => {
    setIsLoading(true);
    await signOut({ redirect: false });
    setTimeout(() => {
      signIn();
    }, 1000);
  };

  return (
    <div className="bg-white p-4 rounded-lg border space-y-4">
      <h3 className="font-semibold">Token Test Controls</h3>

      <div className="space-y-2">
        <button
          onClick={forceTokenRefresh}
          disabled={isLoading}
          className="w-full px-4 py-2 bg-blue-600 text-white rounded hover:bg-blue-700 disabled:opacity-50"
        >
          {isLoading ? 'Testing...' : 'Test Token Refresh (GraphQL Request)'}
        </button>

        <button
          onClick={clearSessionAndSignIn}
          disabled={isLoading}
          className="w-full px-4 py-2 bg-red-600 text-white rounded hover:bg-red-700 disabled:opacity-50"
        >
          Clear Session & Re-authenticate
        </button>
      </div>

      <div className="text-sm text-gray-600">
        <p>
          <strong>Test Token Refresh:</strong> Makes a GraphQL request that will
          trigger token validation and refresh if needed.
        </p>
        <p>
          <strong>Clear Session:</strong> Forces a complete re-authentication
          flow.
        </p>
      </div>
    </div>
  );
}
