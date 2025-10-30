import { auth } from '@/auth';
import { NextResponse } from 'next/server';

export async function proxy(request: any) {
  try {
    const session = await auth();
    
    // If session is null due to invalid refresh token, NextAuth will handle the redirect
    if (!session) {
      console.log('No session found - NextAuth will handle redirect to signin');
    }
    
    // If token refresh failed, redirect to sign in
    if (session?.error === 'RefreshAccessTokenError') {
      console.log('Middleware detected refresh token error, redirecting to signin');
      const signInUrl = new URL('/auth/signin', request.url);
      signInUrl.searchParams.set('callbackUrl', request.url);
      signInUrl.searchParams.set('error', 'SessionExpired');
      return NextResponse.redirect(signInUrl);
    }
    
    // Also check if we have a session but no access token (fallback case)
    if (session?.user && (!session.accessToken || session.accessToken === '')) {
      console.log('Middleware detected session without valid access token, redirecting to signin');
      const signInUrl = new URL('/auth/signin', request.url);
      signInUrl.searchParams.set('callbackUrl', request.url);
      signInUrl.searchParams.set('error', 'TokenExpired');
      return NextResponse.redirect(signInUrl);
    }
    
  } catch (error) {
    console.error('Error in auth middleware:', error);
    // If there's an error getting the session (e.g., session expired), 
    // let NextAuth handle it
  }
  
  // Use the default auth middleware behavior
  return auth(request);
}

export const config = {
  matcher: ['/((?!api|_next/static|_next/image|favicon.ico).*)'],
};
