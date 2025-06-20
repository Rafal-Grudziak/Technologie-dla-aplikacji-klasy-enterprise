using Microsoft.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore.Design;
using Microsoft.Extensions.Configuration;
using System.IO;

namespace MvcNews.Data
{
    public class NewsDbContextFactory : IDesignTimeDbContextFactory<NewsDbContext>
    {
        public NewsDbContext CreateDbContext(string[] args)
        {
            var configuration = new ConfigurationBuilder()
                .SetBasePath(Directory.GetCurrentDirectory())
                .AddJsonFile("appsettings.json")
                .Build();

            var optionsBuilder = new DbContextOptionsBuilder<NewsDbContext>();
            var connectionString = configuration.GetConnectionString("NewsDbContext");
            optionsBuilder.UseSqlite(connectionString);

            return new NewsDbContext(optionsBuilder.Options);
        }
    }
}
