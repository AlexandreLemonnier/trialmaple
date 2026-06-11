<template>
    <div class="min-h-screen bg-app-background py-4 px-2 md:p-8 font-sans">
        <div class="max-w-3xl mx-auto bg-card-background backdrop-blur-sm p-6 md:p-10 rounded-2xl border border-gray-700/60 shadow-xl space-y-8">

            <!-- Header -->
            <div class="border-b border-app-border/80 pb-6">
                <h1 class="text-3xl md:text-4xl w-fit font-black bg-linear-to-r from-primary-indigo to-primary-emerald bg-clip-text text-transparent mb-2">
                    Terms of Service
                </h1>
                <p class="text-xs text-app-text-muted font-medium">Effective Date: June 2026</p>
            </div>

            <!-- Introduction -->
            <p class="text-sm md:text-base leading-relaxed">
                By accessing or using <strong>TM-Maple</strong> ("the Website"), you agree to be bound by these Terms of Service. If you do not agree to these terms, please do not use our service.
            </p>

            <!-- Section 1 -->
            <section class="space-y-3">
                <h2 class="text-xl font-bold flex items-center gap-2">
                    <span class="text-primary-emerald">1. Description of Service</span>
                </h2>
                <p class="text-sm leading-relaxed">
                    TM-Maple is a non-commercial, fan-made daily puzzle game based on the Trackmania video game franchise. It is designed solely for entertainment and videoludic purposes.
                </p>
            </section>

            <!-- Section 2 (disclaimer) -->
            <section class="bg-error/8 rounded-xl p-5 border border-app-border/20 space-y-3">
                <h2 class="text-xl font-bold flex items-center gap-2">
                    <span class="text-error">2. Intellectual Property & Disclaimer</span>
                </h2>
                <div class="text-sm leading-relaxed space-y-2">
                    <p>
                        <strong>Trademark Acknowledgement:</strong> TM-Maple is an <span class="text-error/70 font-semibold">unofficial</span> fan project. <em>Trackmania</em> is a registered trademark of Nadeo and Ubisoft.
                    </p>
                    <p>
                        <strong>No Affiliation:</strong> This Website is not affiliated with, endorsed, sponsored, or specifically approved by Nadeo or Ubisoft. All game logos, graphical assets, and brand names used belong to their respective owners.
                    </p>
                    <p>
                        <strong>Non-Commercial Nature:</strong> This service is entirely free to use and does not generate profit or display advertisements.
                    </p>
                </div>
            </section>

            <!-- Section 3 -->
            <section class="space-y-3">
                <h2 class="text-xl font-bold flex items-center gap-2">
                    <span class="text-primary-emerald">3. User Accounts & Discord</span>
                </h2>
                <p class="text-sm leading-relaxed">
                    To access advanced tracking and persistent statistics, you must log in via Discord OAuth2. You are responsible for maintaining the security of your browser session. We reserve the right to suspend or terminate access to your stats profile if you violate these terms.
                </p>
            </section>

            <!-- Section 4 -->
            <section class="space-y-3">
                <h2 class="text-xl font-bold flex items-center gap-2">
                    <span class="text-primary-emerald">4. Acceptable Use & Fair Play</span>
                </h2>
                <p class="text-sm leading-relaxed">
                    You agree not to exploit the Website or engage in behaviors that harm the user experience, including but not limited to:
                </p>
                <ul class="list-disc list-inside text-sm space-y-1 pl-2">
                    <li>Cheating, modifying network requests, or using automated scripts to submit fake scores.</li>
                    <li>Attempting to reverse-engineer, scrape, or disrupt the backend infrastructure and API endpoints.</li>
                    <li>Using offensive or inappropriate usernames via your connected Discord account.</li>
                </ul>
            </section>

            <!-- Section 5 -->
            <section class="space-y-3">
                <h2 class="text-xl font-bold flex items-center gap-2">
                    <span class="text-primary-emerald">5. Limitation of Liability</span>
                </h2>
                <p class="text-sm leading-relaxed text-app-text-muted italic bg-subcard-background p-4 rounded-xl border border-app-border/20">
                    The Website and its contents are provided on an "as is" and "as available" basis. We do not guarantee that the service will be uninterrupted, secure, or error-free. We are not liable for any loss of gameplay data, statistics, or scores resulting from server maintenance, bugs, database resets, or project closure.
                </p>
            </section>

            <!-- Section 6 -->
            <section class="space-y-3 border-t border-app-border/80 pt-6 text-center">
                <h2 class="text-lg font-bold">Contact & Support</h2>
                <p class="text-sm">
                    If you have any questions regarding these Terms, please reach out to us on Discord: <span class="text-discord select-all">@arsolight</span>
                </p>
            </section>

        </div>
    </div>
</template>
