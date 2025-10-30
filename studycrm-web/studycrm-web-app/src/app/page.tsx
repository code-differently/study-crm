export default async function IndexPage({
  searchParams,
}: {
  searchParams: { q: string };
}) {
  return (
    <main className="p-4 md:p-10 mx-auto max-w-7xl">
      <h1>Homepage</h1>
      <p>This is the homepage.</p>
    </main>
  );
}
