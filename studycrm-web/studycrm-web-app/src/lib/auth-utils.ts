import { signOut, useSession } from 'next-auth/react';

/**
 * Performs a complete OIDC logout that clears both the NextAuth session
 * and the identity provider session cookies.
 */
export async function performOIDCLogout(idToken?: string) {
  // First, sign out from NextAuth locally
  await signOut({ redirect: false });

  // Use iframe approach to call logout endpoint silently, then redirect manually
  // This is necessary because the OIDC server doesn't properly handle post_logout_redirect_uri
  const logoutUrl = constructOIDCLogoutUrl(idToken);

  try {
    // Call logout endpoint via iframe to clear server-side session
    await performLogoutViaIframe(logoutUrl);
  } catch (error) {
    console.warn('Silent logout via iframe failed:', error);
    // Continue anyway - at least NextAuth session is cleared
  }

  // Always redirect to home page after logout
  window.location.href = '/';
}

/**
 * Performs logout by loading the logout URL in a hidden iframe
 */
function performLogoutViaIframe(logoutUrl: string): Promise<void> {
  return new Promise((resolve, reject) => {
    const iframe = document.createElement('iframe');
    iframe.style.display = 'none';
    iframe.src = logoutUrl;

    const timeout = setTimeout(() => {
      document.body.removeChild(iframe);
      reject(new Error('Logout timeout'));
    }, 5000);

    iframe.onload = () => {
      clearTimeout(timeout);
      setTimeout(() => {
        document.body.removeChild(iframe);
        resolve();
      }, 500);
    };

    iframe.onerror = () => {
      clearTimeout(timeout);
      document.body.removeChild(iframe);
      reject(new Error('Iframe load error'));
    };

    document.body.appendChild(iframe);
  });
}

/**
 * Hook that provides a logout function with access to the current session's ID token
 */
export function useOIDCLogout() {
  const { data: session } = useSession();

  return () => {
    const idToken = session?.idToken;
    performOIDCLogout(idToken);
  };
}

/**
 * Constructs the OIDC logout URL with proper parameters
 */
function constructOIDCLogoutUrl(idToken?: string): string {
  const issuerBaseUrl = process.env.NEXT_PUBLIC_STUDYCRM_ISSUER_BASE_URL;
  const clientId = process.env.NEXT_PUBLIC_STUDYCRM_CLIENT_ID;

  if (!issuerBaseUrl) {
    console.error('NEXT_PUBLIC_STUDYCRM_ISSUER_BASE_URL is not defined');
    return '/';
  }

  // Construct the logout URL using the correct OIDC end session endpoint
  const logoutUrl = new URL(`${issuerBaseUrl}/connect/logout`);

  // Add post logout redirect URI - redirect back to home page
  const postLogoutRedirectUri = `${window.location.origin}/`;
  logoutUrl.searchParams.set('post_logout_redirect_uri', postLogoutRedirectUri);

  // Add client_id if available
  if (clientId) {
    logoutUrl.searchParams.set('client_id', clientId);
  }

  // Add id_token_hint if available for a more complete logout
  if (idToken) {
    logoutUrl.searchParams.set('id_token_hint', idToken);
  }

  return logoutUrl.toString();
}

/**
 * Alternative approach: Perform logout by calling NextAuth signOut with
 * a callback URL that points to the OIDC logout endpoint
 */
export async function performOIDCLogoutWithCallback() {
  const oidcLogoutUrl = constructOIDCLogoutUrl();

  // Use NextAuth's signOut with callbackUrl pointing to OIDC logout
  await signOut({
    callbackUrl: oidcLogoutUrl,
    redirect: true,
  });
}
