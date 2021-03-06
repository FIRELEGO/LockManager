USE suncoast[oldsuncoast]
GO

/****** Object:  Table [dbo].[Locks]    Script Date: 9/29/2016 8:24:55 PM ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

SET ANSI_PADDING ON
GO

CREATE TABLE [dbo].[Locks](
	[lock_serial_number] [int] NOT NULL,
	[combo] [char](8) NOT NULL,
 CONSTRAINT [PK_Locks] PRIMARY KEY CLUSTERED 
(
	[lock_serial_number] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO

SET ANSI_PADDING OFF
GO

