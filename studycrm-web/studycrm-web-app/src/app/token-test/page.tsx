import { auth } from '@/auth';
import { redirect } from 'next/navigation';
import { TokenTestControls } from '@/components/token-test-controls';
import { TokenDebugInfo } from '@/components/token-debug-info';

export default async function TokenTestPage() {
  const session = await auth();

  // Handle server-side authentication
  if (!session?.user) {
    redirect('/auth/signin');
  }

  if (session.error === 'RefreshAccessTokenError') {
    redirect('/auth/signin');
  }

  return (
    <div className="container mx-auto p-8">
        <h1 className="text-2xl font-bold mb-6">Token Refresh Test</h1>
        
        <div className="bg-gray-100 p-4 rounded-lg mb-6">
          <h2 className="text-lg font-semibold mb-4">Current Session Info</h2>
          <div className="space-y-2">
            <p><strong>User:</strong> {session?.user?.name || 'N/A'}</p>
            <p><strong>Email:</strong> {session?.user?.email || 'N/A'}</p>
            <p><strong>Organization IDs:</strong> {session?.user?.organizationIds?.join(', ') || 'N/A'}</p>
            <p><strong>Has Access Token:</strong> {session?.accessToken ? 'Yes' : 'No'}</p>
            <p><strong>Has ID Token:</strong> {session?.idToken ? 'Yes' : 'No'}</p>
            <p><strong>Session Error:</strong> {session?.error || 'None'}</p>
          </div>
        </div>

        <div className="bg-blue-50 p-4 rounded-lg mb-6">
          <h2 className="text-lg font-semibold mb-4">Token Expiration Info</h2>
          <p className="text-sm text-gray-600 mb-4">
            With the new configuration:
          </p>
          <ul className="list-disc list-inside space-y-1 text-sm">
            <li>Access tokens expire after 30 minutes</li>
            <li>Refresh tokens expire after 8 hours</li>
            <li>Session max age is 8 hours</li>
            <li>Tokens are refreshed 5 minutes before expiration</li>
          </ul>
        </div>
        
        <TokenDebugInfo />

        <TokenRefreshTester />
        
        <TokenTestControls />
      </div>
  );
}

function TokenRefreshTester() {
  return (
    <div className="bg-yellow-50 p-4 rounded-lg">
      <h2 className="text-lg font-semibold mb-4">Test Instructions</h2>
      <ol className="list-decimal list-inside space-y-2 text-sm">
        <li>Leave this page open for 25+ minutes to test automatic token refresh</li>
        <li>Check the browser console for token refresh logs</li>
        <li>Try making API calls after 25+ minutes to verify tokens are refreshed</li>
        <li>Wait 8+ hours to test refresh token expiration and forced re-authentication</li>
      </ol>
    </div>
  );
}